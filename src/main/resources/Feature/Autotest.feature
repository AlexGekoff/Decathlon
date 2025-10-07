Feature: New Athlete Result
  För att kunna registrera poäng för varje deltagare som deltar i tiokamp/sjukamp

  Scenario Outline: Registrera användare och deras resultat
    Given anvandare oppnar sidan "<browser>"
    When anvandare skriver in sig med fornamn "<firstName>" valjer "<val>" register sina resultat:
      | Event            | Result | Event2           | Result2 |
      | Dec_100m         | 10     | Hep_100mHurdles  | 20      |
      | Dec_400m         | 30     | Hep_200m         | 30      |
      | Dec_1500m        | 160    | Hep_800m         | 80      |
      | Dec_110mHurdles  | 15     | Hep_HighJump     | 200     |
      | Dec_LongJump     | 300    | Hep_LongJump     | 200     |
      | Dec_HighJump     | 70     | Hep_ShotPut      | 20      |
      | Dec_PoleVault    | 500    | Hep_JavelinThrow | 60      |
      | Dec_DiscusThrow  | 50     |                  |         |
      | Dec_JavelinThrow | 60     |                  |         |
      | Dec_ShotPut      | 20     |                  |         |
    And anvandare registrerar sina resultat via excel
    Then resultaten ska vara registrerade för tiokamp/sjukamp

    Examples:
      | browser | firstName | val        |
      | firefox | Adam      | Decathlon  |
      | chrome  | Anna      | Heptathlon |
