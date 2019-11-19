package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread
{
  public Socket socket;
  public DataInputStream inputStream;
  public DataOutputStream outputStream;

  public ConnectionHandler(Socket _s, DataInputStream _is, DataOutputStream _os)
  {
    socket = _s;
    inputStream = _is;
    outputStream = _os;
  }

  @Override
  public void run()
  {
    try
    {
      outputStream.writeUTF("client " + socket + " has own thread");
      while(true) {
        System.out.println(inputStream.readUTF());
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
