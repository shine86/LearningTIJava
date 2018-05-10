//: enumerated/menu/Course.java
package l210802;

import java.util.Random;

public enum Course {
	//¿ªÎ¸²Ë
  APPETIZER(Food.Appetizer.class),
  //Ö÷²Ë
  MAINCOURSE(Food.MainCourse.class),
  //Ìðµã
  DESSERT(Food.Dessert.class),
  //¿§·È
  COFFEE(Food.Coffee.class);
  private Food[] values;
  private Course(Class<? extends Food> kind) {
    values = kind.getEnumConstants();
  }
  public Food randomSelection() {
	Random rand = new Random(47);
	int i = rand.nextInt(values.length);
    return values[i];
  }
} ///:~
