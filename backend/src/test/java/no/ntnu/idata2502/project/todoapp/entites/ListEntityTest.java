package no.ntnu.idata2502.project.todoapp.entites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The ListEntityTest class represents the test class for the ListEntity class. The test class
 * contains positive and negative tests of the list entity.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.05.03)
 * @see ListEntity
 */
public class ListEntityTest {
  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that an instance of the {@link ListEntity} class is correctly structured when the
   * constructor is called.</p>
   */
  @Test
  public void testConstructor() {
    ListEntity list = new ListEntity("Groceries");
    assertEquals("Groceries", list.getTitle());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that the validation method for a {@link ListEntity list entity} returns valid when
   * the entity is constructed with a valid parameter.</p>
   */
  @Test
  public void testValidationMethodWithValidParameter() {
    ListEntity list = new ListEntity("Groceries");
    assertEquals(true, list.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link ListEntity list entity} returns invalid when
   * the entity is constructed with a null parameter.</p>
   */
  @Test
  public void testValidationMethodWithNullParameter() {
    ListEntity list = new ListEntity(null);
    assertEquals(false, list.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link ListEntity list entity} returns invalid when
   * the entity is constructed with an empty parameter.</p>
   */
  @Test
  public void testValidationMethodWithEmptyParameter() {
    ListEntity list = new ListEntity("");
    assertEquals(false, list.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link ListEntity list entity} returns invalid when
   * the entity is constructed with a white space parameter.</p>
   */
  @Test
  public void testValidationMethodWithWithSpaceParameter() {
    ListEntity list = new ListEntity(" ");
    assertEquals(false, list.isValid());
  }
}
