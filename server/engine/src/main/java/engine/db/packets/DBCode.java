package engine.db.packets;
public interface DBCode
{
    // ////////////////client packets//////////////
    short CDBSelectDB = 10000;
    short CDBSaveDirect = 10002;
    short CDBGetObject = 10004;
    short CDBGetObjects = 10005;
    short CDBSaveObjs = 10006;
    short CDBDeleteObjs = 10007;
    short CDBGetConditions = 10008;
    short CDBDeleteObject = 10009;
    short CDBUpdate = 10010;
    short CDBGetAll = 10011;
    short CDBGetFieldValues = 10012;
    short CDBClear = 10013;
    short CDBGetLastID = 10014;
    short CDBGetbyhql = 10015;
    // ////////////////server packets//////////////
    short DBSaveDirect = 10003;
    short DBCheck = 14999;
    short DBError = 15000;
    short DBGetObject = 15003;
    short DBGetObjects = 15004;
    short DBSaveObjects = 15005;
    short DBDelObjects = 15006;
    short DBGetAll = 15007;
    short DBGetFieldValues = 15008;
    short DBClear = 15009;
    short DBLastId = 15010;
}