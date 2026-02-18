package pl.kacper.misterski.rangestats.data.database

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.kacper.misterski.rangestats.data.database.core.AppDatabase
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL("INSERT INTO settings (id, isDarkModes) VALUES (1, 1)")
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true)

        val settingsCursor = db.query("SELECT * FROM settings")
        settingsCursor.moveToFirst()
        assertEquals(1, settingsCursor.getInt(0))
        assertEquals(1, settingsCursor.getInt(1))

        val statisticsCursor = db.query("SELECT * FROM statistics")
        assertEquals(0, statisticsCursor.count)
    }
}