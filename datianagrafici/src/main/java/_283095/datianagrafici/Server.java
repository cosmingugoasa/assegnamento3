package _283095.datianagrafici;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server
{
  static final int server_port = 7777;
  private ServerSocket server;
  private ThreadPoolExecutor pool;
  private static final int corePool = 5;
  private static final int maxPool = 100;
  private static final long idleTime = 5000;

  public static void main(String[] args) throws Exception
  {
    System.out.println("Server starting...");
    new Server().run();
    System.out.println("FINE");
    System.exit(0);
  }

  /*
   *il server aspetta che un client si connetta.
   *crea un thread che gestisca la connessione.
  */
  private void run() throws Exception
  {
    System.out.println("Waiting for something to reply at...");
    server = new ServerSocket(server_port); // crea socket
    pool = new ThreadPoolExecutor(corePool, maxPool, idleTime,
        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    while (true)
    {
      try
      {
        Socket client = server.accept();
        this.pool.execute(new ConnectionHandler(this, client));
        System.out.println("new client : " + client);
        System.out.println("\nNUmeoro Thread:" + pool.getPoolSize());
        // this.pool.shutdown();
      }
      catch (IOException e)
      {
        System.out.println("CHIUSURA");
        break;
      }
    }
  }

  public ThreadPoolExecutor getPool()
  {
    return this.pool;
  }

  public void close()
  {

    try
    {
      this.server.close();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
