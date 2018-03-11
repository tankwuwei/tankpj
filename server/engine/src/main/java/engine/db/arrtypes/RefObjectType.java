package engine.db.arrtypes;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import engine.net.CValue;
import engine.net.NativeBuffer;

public abstract class RefObjectType<T extends CValue> implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.BLOB};
	}

	@Override
	public abstract Class<T> returnedClass() ;

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		byte[] bytes = rs.getBytes(names[0]);
		if(bytes==null) return null;
		NativeBuffer buf = NativeBuffer.wrap(bytes);
		Class<T> c = returnedClass();
		T t = null;
		try {
			t = c.newInstance();
			t.read(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return t;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, 0);
		else {
			@SuppressWarnings("unchecked")
			T t = (T) value;
			NativeBuffer buf = NativeBuffer.allocate();
			t.write(buf);
			byte[] bytes = buf.readBytes();
			st.setBytes(index, bytes);
			buf.free();
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return null;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return null;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return null;
	}

}
