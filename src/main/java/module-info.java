/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  module-info.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * Use "open module" here so FXGL can access the images in resources.
 * For more info see https://github.com/AlmasB/FXGL/wiki/FXGL-11-Migration-Guide#modularity
 */
open module energiequiz.fxgl {
  // Pi4J MODULES
  requires com.pi4j;
  requires com.pi4j.plugin.pigpio;
  requires com.pi4j.plugin.raspberrypi;
  requires com.pi4j.plugin.linuxfs;
  requires com.pi4j.library.pigpio;
  requires com.pi4j.library.linuxfs;

  // SLF4J MODULES
  requires org.slf4j;
  requires org.slf4j.simple;
  requires com.almasb.fxgl.all;

  requires org.json;

  uses com.pi4j.extension.Extension;
  uses com.pi4j.provider.Provider;

  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.annotation;
  requires com.pi4j.plugin.mock;
  requires java.logging;
  requires eu.hansolo.medusa;
  requires javafx.base;
  requires javafx.graphics;

  //requires org.testfx;

  // allow access to classes in the following namespaces for Pi4J annotation processing
  exports energiequiz.fxgl to com.almasb.fxgl.core;
}
