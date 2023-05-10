Feature: To B2C new order creation
  ------------

  @B2Cordercreationsinglelineitem
  Scenario: B2C Create Order with single line Item
    Given I am on an B2C order Creation API with single "single" line Item
    Then I Validate B2C Order Response Code with 1000

  @B2Cordercreationsinglelineitem_IGST
  Scenario Outline: B2C Create Order with single line Item_IGST
    Given I am on an B2C order Creation API with <"single"> line Item_IGST <postalcodeIGST>
    Then I Validate B2C Order Response code with 1000

    Examples: 
      | postalcodeIGST |
      |         600001 |

  @B2Cordercreationsinglelineitem_CGST
  Scenario Outline: B2C Create Order with single line Item_CGST
    Given I am on an B2C order Creation API with <"single"> line Item_CGST <postalcodeCGST>
    Then I VAlidate B2C Order Response code with 1000

    Examples: 
      | postalcodeCGST |
      |         560026 |

  @B2Cordermultiplelineitem
  Scenario: B2C Create Order with multiple line Item
    Given I am on an B2C order Create API with multiple "multiple" line Item
    Then I VAlidate B2C order response code with 1000

  @B2Cordermultiplelineitem_nostock
  Scenario: B2C Create Order with multiple line Item_nostock
    Given I am on an B2C order Create API with multiple "multiple" line Item_nostock
    Then I VAlidate B2C Order Response Code with 1000

  @B2Cordermultiplelineitem_partstock
  Scenario: B2C Create Order with multiple line Item_partstock
    Given I am on an B2C order Create API with multiple "multiple" line Item_partstock
    Then I VAlidate b2c Order Response Code with 1000

  @B2Corderwithsameshippingandbillingaddr
  Scenario: B2C create order with same shipping and billing address
    Given I am on an B2C order create API with "single" same shipping and billing address
    Then I validate b2C order REsponse COde with 1000

  @B2CorderwithdifferentShippingandbillingadd
  Scenario: B2C Create order with different shipping and billing address
    Given I am on an B2C order create API with "single" different shipping and billing address
    Then I Validate b2C order REsponse COde with 1000

  @B2Corderwithwithoutdiscount
  Scenario Outline: B2C Create order with without discount
    Given I am on an B2C order create API with "single" single without discount <Discountper>
    Then I Validate b2C Order REsponse COde with 1000

    Examples: 
      | Discountper |
      |         0.0 |

  @B2Corderwithdiscount
  Scenario Outline: B2C Create order with without discount
    Given I am on an B2C order create API with "single" single with discount <Discountper>
    Then I Validate B2C Order Response COde with 1000

    Examples: 
      | Discountper |
      |         5.9 |

  @B2Corderwithshippingcharge
  Scenario Outline: B2C Create order with shipping charge
    Given I am on an B2c Order create APi with shippling "single" charge <shipplingcharge>
    Then I validate B2C order response Code with 1000

    Examples: 
      | shipplingcharge |
      |           50.00 |

  @B2CorderwithCOD
  Scenario Outline: B2C Create order with COD
    Given I am on an B2c Order create APi with COD "single" <paymentMode>
    Then I validate B2c order response Code with 1000

    Examples: 
      | paymentMode |
      | "COD"       |

  @B2Corderwithprepaid
  Scenario Outline: B2C Create order with prepaid
    Given I am on an B2c Order create APi with prepaid "single" <paymentMode>
    Then I validate b2c order response Code with 1000

    Examples: 
      | paymentMode |
      | "prepaid"    |

  @B2Corderwithoutshippingcharge
  Scenario: B2C Create order with out shipping charge
    Given I am on an B2c Order create APi with out shippling "single" charge
    Then I validate B2C order response COde with 1000

  @B2Cgeneratepicklist
  Scenario: B2C generate picklist with single line Item
    Given I generate B2C Picklist sinlge item
    Then I Validate EXPected response code with 1000

  @B2Cgenerateunpicklist
  Scenario: B2C Order Cancel in picking state
    Given I generate B2C unpicklist
    Then I Validate with The expected ResponSe 1000

  @B2Ccustomerorderhold
  Scenario: B2C customer order hold
    Given I am going to put B2C order on hold
    Then I Validate With the Expected Response 1000

  @B2Ccustomerorderresume
  Scenario: B2C customer order resume
    Given I am going to resume the B2C order
    Then I Validate With the EXpected Response 1000

  @B2Cordercancelinopenstate
  Scenario: B2C Cancel Order in open state with single line Item
    Given I am on an B2C Order Cancel in open state with single line Item
    Then I validate EXPected response code with 1000

  @B2Cmakeinvoicesinglelineitem
  Scenario: B2C make invoice with single line Item
    Given I Generate B2C Invoice
    Then I Validate EXPected Response code with 1000

  @B2Cmakeinvoicemultiplelineitem
  Scenario: B2C make invoice with multiple line Item
    Given I Generate B2C Invoice for multiple line Item
    Then I Validate B2C Order response code with 1000

  @B2Cmakeinvoicewithshortpick
  Scenario: B2C make invoice with shortpick
    Given I generate B2C Invoice with short pick
    Then I validate B2C order response code with 1000

  @B2COrdercancelininvoicestate
  Scenario: B2C Order Cancel in invoice state with Single line Item
    Given I cancel the B2C invoice
    Then I Validate B2C Order REsponse code with 1000

  @B2CupdateshippingstatusDelivered
  Scenario: B2C Update Shipping Status
    Given I update the shipping status as delivered
    Then I Validate With THE Expected Response 1000

  @B2Cupdateshippingstatusin-transit
  Scenario: B2C Update Shipping Status as in-transit
    Given I update the shipping status as in-transit
    Then I validate with THE expected response 1000
