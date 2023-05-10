Feature: To Create C2 customer new order
  ------------

  @CreateCsquareCustomer
  Scenario: Csquare Create New Customer CQ
    Given I am on an new customer Create API
    And I create Csquare Customer
    Then I Validate expected response code with 1000

  @C2customerordersinglelineitem
  Scenario: Csquare Create Customer Order with single line Item
    Given I am on an customer order Create API with single "single" line Item
    Then I validate expected response code with 1000

  @C2customerordersinglelineitem_IGST
  Scenario Outline: Csquare Create Customer Order with single line Item and IGST
    Given I am on an customer order Create API with single "single" line Item and IGST <partyIdIGST>
    Then I validate expected Response Code With 1000

    Examples: 
      | partyIdIGST |
      |     1246213 |

  @C2customerordersinglelineitem_CGST
  Scenario Outline: Csquare Create Customer Order with single line Item and CGST
    Given I am on an customer order Create API with single "single" line Item and CGST <partyIdCGST>
    Then I Validate Expected Response Code With 1000

    Examples: 
      | partyIdCGST |
      |     1759340 |

  @C2customerordersinglelineitem_TO
  Scenario Outline: Csquare Create Customer Order with single line Item with TO
    Given I am on an customer order Create API with single "single" line Item with TO <partyIdTO>
    Then I Validate Expected Response Code WIh 1000

    Examples: 
      | partyIdTO |
      |    310131 |

  @C2customerordermultiplelineitem
  Scenario: Csquare Create Customer Order with multiple line Items
    Given I am On an customer order Create API with multiple "multiple" line Items
    Then I validate expected response code With 1000

  @C2customerordermultiplelineitem-partstock
  Scenario: Csquare Create Customer Order with multiple line Items-partstock
    Given I am on an customer order Create API with multiple "multiple" line Items-partstock
    Then I VALidate expected response code With 1000

  @C2customerordermultiplelineitemnostock
  Scenario: Csquare Create Customer Order with multiple line Item with no stock
    Given I am on an customer order Create API with multiple "multiple" line Items with no stock
    Then I Validate expected Response code with 1000

  @C2generatepicklist
  Scenario: Csquare generate picklist with single line Item
    And I generate Picklist
    Then I validate with the expected response 1000

  @C2generateunpicklist
  Scenario: Csquare Customer Order Cancel in picking state
    And I generate unpicklist
    Then I validate with the expected ResponSe 1000

  @C2makeinvoicesinglelineitem
  Scenario: Csquare make invoice with single line Item
    And I Generate Invoice
    Then I validate with the expected Response code 1000

  @C2makeinvoicemultiplelineitem
  Scenario: Csquare make invoice with multiple line Item
    And I Generate invoice
    Then I validate with the expected Response Code 1000

  @C2makeinvoicewithshortpick
  Scenario: Csquare make invoice with shortpic
    Given I Generate Invoice with short pick
    Then I validate with THe expected Response code 1000

  @C2customerordercancelinopenstate
  Scenario: Csquare Customer Order Cancel in open state
    And I cancel order in open state
    Then I validate with the Expected Response 1000

  @C2customerordercancelininvoicestate
  Scenario: Csquare Customer Order Cancel in invoice state with Single line Items_IGST
    And I Cancel The invoice
    Then I Validate With the expected Response code 1000

  @C2updateshippingstatusDelivered
  Scenario: Csquare Update Shipping Status
    Given I Update the Shipping Status
    Then I Validate with the Expected Response 1000

  @C2updateshippingstatusin-transit
  Scenario: Csquare Update Shipping Status as in-transit
    Given I Update the Shipping Status as in-transit
    Then I Validate With The Expected Response 1000
