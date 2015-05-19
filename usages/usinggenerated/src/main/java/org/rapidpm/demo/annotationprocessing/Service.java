package org.rapidpm.demo.annotationprocessing;

import org.rapidpm.demo.annotationprocessing.doa.DynamicObjectAdapterBuilder;

/**
 * Created by sven on 13.05.15.
 */
@DynamicObjectAdapterBuilder
public interface Service {
  String doWork_A(String txt);

  String doWork_B(String txt);
}
