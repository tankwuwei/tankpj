package engine.db.packets;
import java.util.*;

import engine.db.packets.client.*;
import engine.db.packets.server.*;
import engine.net.*;

public class DBClientPackets
{
    private static final Map<Integer,CPacket> packets=new HashMap<Integer,CPacket>();
    static
    {
        packets.put(10000,new CDBSelectDB());
        packets.put(10002,new CDBSaveDirect());
        packets.put(10003,new DBSaveDirect());
        packets.put(10004,new CDBGetObject());
        packets.put(10005,new CDBGetObjects());
        packets.put(10006,new CDBSaveObjs());
        packets.put(10007,new CDBDeleteObjs());
        packets.put(10008,new CDBGetConditions());
        packets.put(10009,new CDBDeleteObject());
        packets.put(10010,new CDBUpdate());
        packets.put(10011,new CDBGetAll());
        packets.put(10012,new CDBGetFieldValues());
        packets.put(10013,new CDBClear());
        packets.put(10014,new CDBGetLastID());
        packets.put(10015,new CDBGetbyhql());
        packets.put(14999,new DBCheck());
        packets.put(15000,new DBError());
        packets.put(15003,new DBGetObject());
        packets.put(15004,new DBGetObjects());
        packets.put(15005,new DBSaveObjects());
        packets.put(15006,new DBDelObjects());
        packets.put(15007,new DBGetAll());
        packets.put(15008,new DBGetFieldValues());
        packets.put(15009,new DBClear());
        packets.put(15010,new DBLastId());
    }
    
    public static CPacket get(engine.net.NativeBuffer buf)
    {
        int code=buf.readShort();
        CPacket packet=packets.get(code);
        if(packet==null) packet=new CPacket();
        else
        {
            try
            {
                packet=(CPacket)packet.clone();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        packet.code=code;
        packet.read(buf);
        return packet;
    }
}