package engine.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * JSON 转换相关的工具类 注意,Map的Key只能为简单类型 ,不可采用复杂类型.
 * 
 * @author qu.yy
 */
@SuppressWarnings("unchecked")
public final class JsonUtils {

	private JsonUtils() {
		throw new IllegalAccessError("该类不允许实例化");
	}

	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

	private static TypeFactory typeFactory = TypeFactory.defaultInstance();

	private static final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

	/**
	 * 将对象转换为 JSON 的字符串格式
	 * 
	 * @param object
	 * @return
	 */
	public static String object2String(Object object) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, object);
		} catch (Exception e) {
			log.error("将 object 转换为 json 字符串时发生异常", e);
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	/**
	 * 将 map 转换为 JSON 的字符串格式
	 * 
	 * @param map
	 * @return
	 */
	public static String map2String(Map<?, ?> map) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, map);
		} catch (Exception e) {
			log.error("将 map 转换为 json 字符串时发生异常", e);
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

	/**
	 * 将 JSON 格式的字符串转换为 map
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, Object> string2Map(String content) {
		JavaType type = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
		// JavaType type = TypeFactory.mapType(HashMap.class, String.class, Object.class);
		try {
			return mapper.readValue(content, type);
		} catch (Exception e) {
			FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为Map时出现异常", content);
			log.error(message.getMessage(), e);
			throw new RuntimeException(message.getMessage(), e);
		}
	}

	/**
	 * 将 JSON 格式的字符串转换为数组
	 * 
	 * @param <T>
	 * @param content 字符串
	 * @param clz 数组类型
	 * @return
	 */
	public static <T> T[] string2Array(String content, Class<T> clz) {
		JavaType type = ArrayType.construct(typeFactory.constructType(clz));
		// JavaType type = TypeFactory.arrayType(clz);
		try {
			return (T[]) mapper.readValue(content, type);
		} catch (Exception e) {
			FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为数组时出现异常", content, e);
			log.error(message.getMessage(), e);
			throw new RuntimeException(message.getMessage(), e);
		}
	}

	/**
	 * 将 JSON 格式的字符串转换为对象
	 * 
	 * @param <T>
	 * @param content 字符串
	 * @param clz 对象类型
	 * @return
	 */
	public static <T> T string2Object(String content, Class<T> clz) {
		JavaType type = typeFactory.constructType(clz);
		// JavaType type = TypeFactory.fastSimpleType(clz);
		try {
			return (T) mapper.readValue(content, type);
		} catch (Exception e) {
			FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为对象[{}]时出现异常",
					new Object[] { content, clz.getSimpleName(), e });
			log.error(message.getMessage(), e);
			throw new RuntimeException(message.getMessage(), e);
		}
	}

	/**
	 * 将 JSON 格式的字符串转换为集合
	 * 
	 * @param <T>
	 * @param content 字符串
	 * @param collectionType 集合类型
	 * @param elementType 元素类型
	 * @return
	 */
	public static <C extends Collection<E>, E> C string2Collection(String content, Class<C> collectionType,
			Class<E> elementType) {
		try {
			JavaType type = typeFactory.constructCollectionType(collectionType, elementType);
			// JavaType type = TypeFactory.collectionType(collectionType, elementType);
			return mapper.readValue(content, type);
		} catch (Exception e) {
			FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为集合[{}]时出现异常", new Object[] { content,
					collectionType.getSimpleName(), e });
			log.error(message.getMessage(), e);
			throw new RuntimeException(message.getMessage(), e);
		}
	}

	public static <K, V> Map<K, V> string2Map(String content, Class<K> keyType, Class<V> valueType) {
		JavaType type = typeFactory.constructMapType(HashMap.class, keyType, valueType);
		// JavaType type = TypeFactory.mapType(HashMap.class, String.class, Object.class);
		try {
			return (Map<K, V>) mapper.readValue(content, type);
		} catch (Exception e) {
			FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为Map时出现异常", content);
			log.error(message.getMessage(), e);
			throw new RuntimeException(message.getMessage(), e);
		}
	}
	
	public static String getValue(Map<String, Object> map, String[] path){
		Object obj = null;
		for (int i = 0; i< path.length; i++){
			obj = map.get(path[i]);
			if(obj == null)return null;
			else if(obj instanceof ArrayList){
				obj = ((ArrayList<?>)obj).get(0);
			}
			if(obj instanceof Map){
				map = (Map<String, Object>)obj;
			}
		}
		if(obj != null && obj instanceof String)return (String)obj;
		return null;
	}
}
