package eu.stolin.wd2

import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.core.Is
import org.junit.Assert.assertThat
import org.junit.Test


class PersonParserTest {


    @Test
    fun `parse single gender and name`() {
        val result = PersonParser.parsePersons("F:Alice")
        assertThat(result, hasSize(1))
        assertThat(result.first(), Is.`is`(Person(Gender.FEMALE, "Alice")))
    }

    @Test
    fun `parse more names`() {
        val result = PersonParser.parsePersons("F:Alice,M:Bob,M:Charlie,F:Daisy")
        assertThat(result, hasSize(4))
    }
}