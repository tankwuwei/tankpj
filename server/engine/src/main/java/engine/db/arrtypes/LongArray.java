package engine.db.arrtypes;

import engine.util.Bytes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public class LongArray extends ArrayType{

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return long[].class;
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		return Bytes.longArray(rs.getBytes(names[0]));
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, 0);
		else {
			st.setBytes(index, Bytes.get((long[])value));
		}
	}
}