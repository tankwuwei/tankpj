package engine.db.arrtypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

import engine.util.Bytes;
/**
 * a Set<Long> type.
 * 
 * @author sunnyawake
 *
 */
public class LongSet extends ArrayType{

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return HashSet.class;
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		return Bytes.longSet(rs.getBytes(names[0]));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, 0);
		else {
			st.setBytes(index, Bytes.get((Set<Long>)value));
		}
	}
}

