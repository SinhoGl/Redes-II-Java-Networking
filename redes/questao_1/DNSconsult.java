package redes.questao_1;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class DNSconsult{
    public static void main(String[] args) throws UnknownHostException{

        //Google
        System.out.println(InetAddress.getByName("www.google.com"));
        Arrays.asList(InetAddress.getAllByName("www.google.com")).stream().forEach(e -> System.out.println(e));
        System.out.println("Quantidade de IPs relacionados: "+InetAddress.getAllByName("www.google.com").length);

        System.out.println(" ");

        //Youtube
        System.out.println(InetAddress.getByName("www.Youtube.com"));
        Arrays.asList(InetAddress.getAllByName("www.Youtube.com")).stream().forEach(e -> System.out.println(e));
        System.out.println("Quantidade de IPs relacionados: "+InetAddress.getAllByName("www.Youtube.com").length);
    }
}

