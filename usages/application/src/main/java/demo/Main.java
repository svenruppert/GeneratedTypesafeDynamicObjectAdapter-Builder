package demo;

import org.rapidpm.demo.annotationprocessing.Service;
import org.rapidpm.demo.annotationprocessing.ServiceAdapterBuilder;
import org.rapidpm.demo.annotationprocessing.ServiceImpl;
import org.rapidpm.demo.annotationprocessing.Service_doWork_A;

/**
 * Created by sven on 18.05.15.
 */
public class Main {


  public static void main(String[] args) {

//    final Service_doWork_A service_doWork_a = new Service_doWork_A();
    Service_doWork_A doWork_a = new Service_doWork_A() {
      @Override
      public String doWork_A(String txt) {
        return null;
      }
    };


    final Service service = ServiceAdapterBuilder
        .newBuilder()
        .setOriginal(new ServiceImpl())
        .withDoWork_A(txt -> "DOA-Builder Method A " + txt)
        .buildForTarget(Service.class);

    System.out.println(service.doWork_B("XX"));
    System.out.println(service.doWork_A("XX"));

  }
}
