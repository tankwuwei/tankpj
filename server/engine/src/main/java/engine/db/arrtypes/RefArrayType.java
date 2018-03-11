package engine.db.arrtypes;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

import engine.net.CValue;
import engine.net.NativeBuffer;

public abstract class RefArrayType<T extends CValue> extends ArrayType {
	@SuppressWarnings("rawtypes")
	public abstract Class returnedClass();
	
	@SuppressWarnings("unchecked")
	public Class<T> returnCompnentClass(){
		@SuppressWarnings("rawtypes")
		Class c = returnedClass();
		if(c.isArray()){
			return c.getComponentType();
		}
		return null;
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		byte[] bytes = rs.getBytes(names[0]);
		if(bytes==null) return null;
		NativeBuffer buf = NativeBuffer.wrap(bytes);
		int length = buf.readShort();
		Class<T> c = returnCompnentClass();
		@SuppressWarnings("unchecked")
		T[] arr = (T[])Array.newInstance(c, length);
		try {
			for (int i = 0; i < arr.length; i++) {
				T t = c.newInstance();
				t.read(buf);
				arr[i] = t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		buf.free();
		return arr;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, 0);
		else {
			
			@SuppressWarnings("unchecked")
			T[] array = (T[])value;
			NativeBuffer buf = NativeBuffer.allocate();
			buf.writeShort(array.length);
			for (int i = 0; i < array.length; i++) {
				array[i].write(buf);
			}
			byte[] bytes = buf.readBytes();
			st.setBytes(index, bytes);
			buf.free();
		}
	}
}
