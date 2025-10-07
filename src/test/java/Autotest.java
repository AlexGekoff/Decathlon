
import com.example.decathlon.Auto.MainWeb;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import java.util.List;
import java.util.Map;
import  io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;


public class Autotest {



    //kallar mainwebb vilken webbläsare vill man ja
    public static class stegdefauto {
        @Given("anvandare oppnar sidan {string}")
        public void anvandareOppnarSidan(String browser) {
            WebDriver driver = MainWeb.initializeDriver(browser);
            MainWeb.setDriver(driver);
            driver.get("http://localhost:8080/");
        }
    }

    //La in en forloop eftersom data inte sparades
    @When("anvandare skriver in sig med fornamn {string} valjer {string} register sina resultat:")
    public void anvandareSkriverInSigMedFornamnValjerRegisterSinaResultat(String firstName, String val, DataTable dataTable) {
        //Lägger en lista som följer en önskad rad parallelt
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String event = "";
            String res = "";
            //If-sats som följer de olika listorna utifrån val av gren
            if (val.equals("Decathlon")) {
                event = row.get("Event");
                res = row.get("Result");
            } else if (val.equals("Heptathlon")) {
                event = row.get("Event2");
                res = row.get("Result2");
            }


            //Skriver in Namn
            MainWeb.getDriver().findElement(By.id("name2")).sendKeys(firstName);

            //väljer Decathlon eller Heptathlon
            WebElement selectElement = MainWeb.getDriver().findElement(By.id("category"));
            Select dropdown = new Select(selectElement);
            dropdown.selectByVisibleText(val);


            // Välj event i dropdownen
            WebElement eventDropdown = MainWeb.getDriver().findElement(By.id("event"));
            Select selectEvent = new Select(eventDropdown);
            selectEvent.selectByVisibleText(event);

            // Ange resultat
            WebElement resultInput = MainWeb.getDriver().findElement(By.id("raw"));
            resultInput.clear();
            resultInput.sendKeys(res);

            // Klicka på spara-knappen
            WebElement saveButton = MainWeb.getDriver().findElement(By.id("save"));
            saveButton.click();


        }
    }
    //finns för att lägga någon mer steg
    @And("anvandare registrerar sina resultat via excel")
    public void anvandareRegistrerarSinaResultatViaExcel() {
    }
    //Har then för eventuella tilläg för att kanske jämföra
    @Then("resultaten ska vara registrerade för tiokamp\\/sjukamp")
    public void resultatenSkaVaraRegistreradeForTiokampSjukamp() {
    }
}