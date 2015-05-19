# GeneratedTypesafeDynamicObjectAdapter-Builder
How to generate a typesafe DynamicObjectAdapter-Builder per AnnotationProcessing. 
(a detailed description will come soon.....  follow me  [@SvenRuppert](https://twitter.com/SvenRuppert) )
an german version you could find under [GitBook - JavaFundamentals - GeneratedTypesaveDynamicObjectAdapter-Builder](http://sven-ruppert.gitbooks.io/kurse/content/java-core/chapter-java-core-pattern-creational-builder-dynamic-object-adapter-builder-generated.html)

1. mvn clean install at module genericbasics
2. mvn clean install at module usages

This is an demo of how to use AnnotationProcessing to generate a DynamicObjectAdapter-Builder.
This is used inside the project [rapidpm/proxybuilder](https://github.com/RapidPM/proxybuilder).

What you could do with this:

Annotate an Interface with ***@DynamicObjectAdapterBuilder***. 
```java
@DynamicObjectAdapterBuilder
public interface Service {
  String doWork_A(String txt);
  String doWork_B(String txt);
}
public class ServiceImpl implements Service {
  @Override
  public String doWork_A(String txt) {
    return "doWorkd_A_Original";
  }

  @Override
  public String doWork_B(String txt) {
    return "doWorkd_B_Original";
  }
}
```
compile will create ...
```java
@FunctionalInterface
public interface Service_doWork_A {
  String doWork_A(final String txt);
}

@FunctionalInterface
public interface Service_doWork_B {
  String doWork_B(final String txt);
}

public class ServiceInvocationHandler extends ExtendedInvocationHandler<Service> {
  public void doWork_A(final Service_doWork_A adapter) {
    addAdapter(adapter);
  }

  public void doWork_B(final Service_doWork_B adapter) {
    addAdapter(adapter);
  }
}

public class ServiceAdapterBuilder extends AdapterBuilder<Service> {
  private final ServiceInvocationHandler invocationHandler = new ServiceInvocationHandler();

  public static ServiceAdapterBuilder newBuilder() {
    return new ServiceAdapterBuilder();
  }

  @Override
  public ServiceInvocationHandler getInvocationHandler() {
     return invocationHandler;
  }

  public ServiceAdapterBuilder setOriginal(final Service original) {
    invocationHandler.setOriginal(original);
    return this;
  }

  public ServiceAdapterBuilder withDoWork_A(final Service_doWork_A adapter) {
    invocationHandler.doWork_A(adapter);
    return this;
  }

  public ServiceAdapterBuilder withDoWork_B(final Service_doWork_B adapter) {
    invocationHandler.doWork_B(adapter);
    return this;
  }
}
````

Now you could use the DynamicObjectAdapter-Builder like the following.

```java
  public static void main(String[] args) {

    Service service = ServiceAdapterBuilder.newBuilder()
        .setOriginal(new ServiceImpl())
        .withDoWork_A((txt) -> txt + "_part")
        .buildForTarget(Service.class);

    System.out.println(service.doWork_A("Hallo Adapter"));

    final boolean proxyClass = Proxy.isProxyClass(service.getClass());
    System.out.println("proxyClass = " + proxyClass);

    //Interface auf den InvocactionHandler
    final InvocationHandler invocationHandler = Proxy.getInvocationHandler(service);
    final ServiceInvocationHandler serviceInvocationHandler = (ServiceInvocationHandler) invocationHandler;

    serviceInvocationHandler.doWork_A((txt) -> txt + "_part_modified");
    System.out.println(service.doWork_A("Hallo Adapter"));

    final Service serviceX = ServiceAdapterBuilder
        .newBuilder()
        .setOriginal(new ServiceImpl())
        .withDoWork_A(txt -> "DOA-Builder Method A " + txt)
        .buildForTarget(Service.class);

    System.out.println(serviceX.doWork_A("XX"));
  }
````

