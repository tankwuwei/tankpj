package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTeamPlayer extends CPacket
{
    public SCTeamPlayer()
    {
        code=7028;
    }
    
    public  TeamPlayer[] teamplayer;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int teamplayerLength=buf.readShort();
        teamplayer=new TeamPlayer[teamplayerLength];
        for(int i=0;i<teamplayerLength;i++)
        {
            TeamPlayer d=new TeamPlayer();
            if(buf.readByte()==1)
            {
                d.read(buf);
                teamplayer[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(teamplayer);
    }
}