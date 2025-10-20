Feature: New Athlete Result
  För att kunna registrera poäng för 40 deltagare som deltar i tiokamp/sjukamp random

  Scenario Outline: Registrera användare och deras resultat
    Given anvandare oppnar webblasaren "<browser>"
    When anvandare skriver in sig med namn "<gender>" paverkar event sem registeras resultat
    And anvandare ser meddelande
    Then anvandare registrerar sina resultat via excel
    Examples:
      | browser | gender |
      | firefox |        |