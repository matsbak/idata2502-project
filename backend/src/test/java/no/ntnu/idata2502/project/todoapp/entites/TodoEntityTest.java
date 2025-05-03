package no.ntnu.idata2502.project.todoapp.entites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The TodoEntityTest class represents the test class for the TodoEntity class. The test class
 * contains positive and negative tests of the todo entity.
 * 
 * @author Candidate 10006
 * @version v1.0.0 (2025.05.03)
 * @see TodoEntity
 */
public class TodoEntityTest {
  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that an instance of the {@link TodoEntity} class is correctly structured when the
   * constructor is called.</p>
   */
  @Test
  public void testConstructor() {
    TodoEntity todo = new TodoEntity("Milk");
    assertEquals("Milk", todo.getDescription());
    assertEquals(false, todo.isComplete());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that the completion status of a {@link TodoEntity todo entity} is changed when the
   * set method for this field is called.</p>
   */
  @Test
  public void testCompleteSetMethod() {
    TodoEntity todo = new TodoEntity("Milk");
    todo.setComplete(true);
    assertEquals(true, todo.isComplete());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that the list containing a {@link TodoEntity todo entity} is changed when the set
   * method for the field is called.</p>
   */
  @Test
  public void testListSetMethod() {
    TodoEntity todo = new TodoEntity("Milk");
    ListEntity list = new ListEntity("Groceries");
    todo.setList(list);
    assertEquals(list, todo.getList());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Tests that the validation method for a {@link TodoEntity todo entity} returns valid when
   * the entity is constructed with a valid parameter.</p>
   */
  @Test
  public void testValidationMethodWithValidParameter() {
    TodoEntity todo = new TodoEntity("Milk");
    assertEquals(true, todo.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link TodoEntity todo entity} returns invalid when
   * the entity is constructed with a null parameter.</p>
   */
  @Test
  public void testValidationMethodWithNullParameter() {
    TodoEntity todo = new TodoEntity(null);
    assertEquals(false, todo.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link TodoEntity todo entity} returns invalid when
   * the entity is constructed with an empty parameter.</p>
   */
  @Test
  public void testValidationMethodWithEmptyParameter() {
    TodoEntity todo = new TodoEntity("");
    assertEquals(false, todo.isValid());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Tests that the validation method for a {@link TodoEntity todo entity} returns invalid when
   * the entity is constructed with a white space parameter.</p>
   */
  @Test
  public void testValidationMethodWithWhiteSpaceParameter() {
    TodoEntity todo = new TodoEntity(" ");
    assertEquals(false, todo.isValid());
  }
}
