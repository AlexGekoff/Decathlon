
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import com.example.decathlon.Auto.MainWeb;
import com.example.decathlon.Athletes.RandomAthletes;


import org.junit.AfterClass;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import  io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;
import static com.example.decathlon.Auto.MainWeb.quitDriver;


public class Autotest {

    //Lägger till en Randomgenerator inom de önskande gränsvärderna
    private int generateResultInRange(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }


    //la in en try and catch eftersom man inte fick in siffor under testkörning

    private void fillEventResult(String eventId, int result) {
        WebDriver driver = MainWeb.getDriver();
        try {
            WebElement resultInput = driver.findElement(By.id("raw")); // eller By.id(eventId) om du använder dynamiska id:n
            resultInput.clear();
            resultInput.sendKeys(String.valueOf(result));
            System.out.println("Resultat fyllt: " + result);
        } catch (NoSuchElementException e) {
            System.out.println("Element med id 'raw' hittades inte.");
        }
    }




    //kallar mainwebb vilken webbläsare vill man ja

    @Given("anvandare oppnar sidan {string}")
    public void anvandareOppnarSidan(String browser) {
        WebDriver driver = MainWeb.initializeDriver(browser);
        MainWeb.setDriver(driver);
        driver.get("http://localhost:8080/");
    }

    @Given("anvandare oppnar webblasaren {string}")
    public void anvandareOppnarWebblasaren(String browser ) {
        WebDriver driver = MainWeb.initializeDriver(browser);
        MainWeb.setDriver(driver);
        driver.get("http://localhost:8080/");
    }
    //La in en for loop eftersom data inte sparades
    @When("anvandare skriver in sig med fornamn {string} valjer {string} register sina resultat:")
    public void anvandareSkriverInSigMedFornamnValjerRegisterSinaResultat(String firstName, String val, DataTable dataTable) {

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String event = "";
            String res = "";

            if (val.equals("Decathlon")) {
                event = row.get("Event");
                res = row.get("Result");
            } else if (val.equals("Heptathlon")) {
                event = row.get("Event2");
                res = row.get("Result2");
            }


            //Skriver in namn igen
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

    @When("anvandare skriver in sig med namn {string} paverkar event sem registeras resultat")
    public void anvandareSkriverInSigMedNamnPaverkarEventSemRegisterasResultat(String gender) {

        int maxParticipants = 40; // ändra här för önskat maxantal
        int beginCount = 0;

        //Dropdowngrenarna för varje Event
        List<String> decathlonEvents = Arrays.asList(
                "Dec_100m",
                "Dec_400m",
                "Dec_1500m",
                "Dec_110mHurdles",
                "Dec_LongJump",
                "Dec_HighJump",
                "Dec_PoleVault",
                "Dec_DiscusThrow",
                "Dec_JavelinThrow",
                "Dec_ShotPut"
        );

        List<String> heptathlonEvents = Arrays.asList(
                "Hep_100mHurdles",
                "Hep_200m",
                "Hep_800m",
                "Hep_HighJump",
                "Hep_LongJump",
                "Hep_ShotPut",
                "Hep_JavelinThrow"
        );
        //Antal deltagare som ska delta som jämnar ut könerna
        List<String> genders = Arrays.asList("male", "female");
        int numberOfParticipants = 40;
        int participantsPerGender = numberOfParticipants / genders.size();
        /*For loop för att köra igenom gen vill man ha endast female eller male
        kan man ta bort for-loopen och endast ha int numberofParticipants*/
        for (String gen : genders) {
            //For loop som hämtar deltagarna från klassen randomAthletes
            for (int i = 0; i < numberOfParticipants; i++) {
                // If-sats för att avsluta antal deltagare
                if (beginCount >=maxParticipants){
                    break;
                }
                // Generera ett slumpmässigt namn för varje deltagare från RandomAthlete
                String name = RandomAthletes.generateRandomName(gen);

                /*Generera ett slumpmässigt namn för varje deltagare från RandomAthlete och
                 kan välja gender beroende önskning tysta den för att de ska finnas som val*/
                // String name = RandomAthletes.generateRandomName(gender);

                // Fyller i namn i textformuläret och förkortade för inhämtning av mainweb
                WebDriver driver = MainWeb.getDriver();
                WebElement nameField = driver.findElement(By.id("name2"));
                // rensa fältet först
                nameField.clear();
                nameField.sendKeys(name);

                // Väljer gren (decathlon eller heptathlon) beroende på female eller male

                WebElement categoryDropdown = driver.findElement(By.id("category"));
                /*Allt som är gender tystades ner för att de skulle finnas som val om man önskar endast
                male eller female */

                // new Select(categoryDropdown).selectByVisibleText(gender.equalsIgnoreCase("male") ? "Decathlon" : "Heptathlon");
                new Select(categoryDropdown).selectByVisibleText(gen.equalsIgnoreCase("male") ? "Decathlon" : "Heptathlon");

                // Välj rätt lista baserat på kön
                // List<String> selectedEvents = gender.equalsIgnoreCase("male") ? decathlonEvents : heptathlonEvents;
                List<String> selectedEvents = gen.equalsIgnoreCase("male") ? decathlonEvents : heptathlonEvents;
                // Loop för att fylla i resultat för varje gren
                for (String eventId : selectedEvents) {
                    int min = 0;
                    int max = 0;

                    switch (eventId) {
                        //Case för Decathlon-Events
                        case "Dec_100m":
                            min = 5;
                            max = 20;
                            break;
                        case "Dec_110mHurdles":
                            min = 10;
                            max = 30;
                            break;
                        case "Dec_400m":
                            min = 20;
                            max = 100;
                            break;
                        case "Dec_1500m":
                            min = 150;
                            max = 400;
                            break;
                        case "Dec_DiscusThrow":
                            min = 0;
                            max = 85;
                            break;
                        case "Dec_HighJump":
                            min = 0;
                            max = 300;
                            break;
                        case "Dec_JavelinThrow":
                            min = 0;
                            max = 110;
                            break;
                        case "Dec_LongJump":
                            min = 0;
                            max = 1000;
                            break;
                        case "Dec_PoleVault":
                            min = 0;
                            max = 1000;
                            break;
                        case "Dec_ShotPut":
                            min = 0;
                            max = 30;
                            break;
                        // case för heptathlon-Events
                        case "Hep_100mHurdles":
                            min = 10;
                            max = 30;
                            break;
                        case "Hep_200m":
                            min = 20;
                            max = 100;
                            break;
                        case "Hep_800m":
                            min = 70;
                            max = 250;
                            break;
                        case "Hep_HighJump":
                            min = 0;
                            max = 300;
                            break;
                        case "Hep_JavelinThrow":
                            min = 0;
                            max = 110;
                            break;
                        case "Hep_LongJump":
                            min = 0;
                            max = 1000;
                            break;
                        case "Hep_ShotPut":
                            min = 0;
                            max = 30;
                            break;

                    }
                    //Upprepar att lägga in de igen för att efter andra körningen i applikationen så glömmer de gren och namn
                    nameField.clear();
                    nameField.sendKeys(name);

                    //new Select(categoryDropdown).selectByVisibleText(gender.equalsIgnoreCase("male") ? "Decathlon" : "Heptathlon");
                    new Select(categoryDropdown).selectByVisibleText(gen.equalsIgnoreCase("male") ? "Decathlon" : "Heptathlon");
                    // väljer gren från lista och generera random nummer efter gränsvärderna i case
                    int result = generateResultInRange(min, max);
                    fillEventResult(eventId, result);

                    // Väljer gren i dropdownen
                    WebElement eventDropdown = MainWeb.getDriver().findElement(By.id("event"));
                    Select selectEvent = new Select(eventDropdown);
                    selectEvent.selectByVisibleText(eventId);

                    // Fyll i resultat i inputfältet
                    WebElement resultInput = MainWeb.getDriver().findElement(By.id("raw"));
                    resultInput.clear();
                    resultInput.sendKeys(String.valueOf(result));

                    // Klicka på spara-knappen
                    WebElement saveButton = MainWeb.getDriver().findElement(By.id("save"));
                    saveButton.click();


                }

                beginCount++;


            }

        }
    }

    // För 40 max medlemmar
    @And("anvandare ser meddelande")
    public void anvandareSerMeddelande() {
    }

    //finns för att lägga någon mer steg och kolla excel
    @And("anvandare registrerar sina resultat via excel")
    public void anvandareRegistrerarSinaResultatViaExcel() {
     /*   WebElement excelButton = MainWeb.getDriver().findElement(By.id("export"));
        excelButton.click();*/
    }

    //Har then för eventuella tilläg för att kanske jämföra

    @Then("resultaten ska vara registrerade för tiokamp\\/sjukamp")
    public void resultatenSkaVaraRegistreradeForTiokampSjukamp() {
    }
    //
    @AfterClass
    public static void tearDown() {
        quitDriver();
    }

}