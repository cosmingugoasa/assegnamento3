package _283095.datianagrafici;

import java.io.IOException;
import java.text.ParseException;

public class Main
{

  public static void main(String[] args) throws IOException, ParseException
  {
    try
    {
      Thread _client1 = new Client("admin@gmail.com", "rr");
      _client1.start();
      Thread.sleep(7000);
      Thread _client2 = new Client("dax@gmail.com", "rr");
      _client2.start();
      
    }
    catch (ClassNotFoundException | InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
