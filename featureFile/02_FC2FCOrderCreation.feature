Feature: To FC2FC new order creation
  ------------

  @FC2FCordersinglelineitem
  Scenario: FC2FC Create Order with single line Item
    Given I am on an FC2FC order Create API with single line Item
    Then I VAlidate expected response code with 1000

  @FC2FCordermultiplelineitem
  Scenario: FC2FC Create Order with multiple line Item
    Given I am on an FC2FC order Create API with multiple line Item
    Then I VAlidate Expected response code with 1000

  @FC2FCordermultiplelineitem_partstock
  Scenario: FC2FC Create Order with multiple line Item_part stock
    Given I am on an FC2FC order Create API with multiple line Item_part stock
    Then I VAlidate Expected response COde with 1000

  @FC2FCordermultiplelineitem_nostock
  Scenario: FC2FC Create Order with multiple line Item_no stock
    Given I am on an FC2FC order Create API with multiple line Item_no stock
    Then I VAlidate EXpected response COde with 1000

  @FC2FCordersinglelineitem_IGST
  Scenario Outline: FC2FC Create Order with single line Item_IGST
    Given I am on an FC2FC order Create API with single "single" line Item_IGST <SupplierID>
    Then I VAlidate EXpected response code with 1000

    Examples: 
      | SupplierID |
      |        768 |

  @FC2FCordersinglelineitem_CGST
  Scenario Outline: FC2FC Create Order with single line Item_CGST
    Given I am on an FC2FC order Create API with single "single"  line Item_CGST <SupplierID>
    Then I VAlidate EXpected Response code with 1000
    
      Examples: 
      | SupplierID |
      |        768 |

  @FC2FCgeneratepicklist
  Scenario: FC2FC generate picklist with single line Item
    Given I generate FC2FC Picklist
    Then I VAlidate with the expected response 1000

  @FC2FCgenerateunpicklist
  Scenario: FC2FC Order Cancel in picking state
    Given I generate FC2FC unpicklist
    Then I Validate with THe expected ResponSe 1000

  @FC2FCtransferoutsinglelineitem
  Scenario: FC2FC make transferout with single line Item
    Given I Generate FC2FC transferout
    Then I VAlidate with the expected Response code 1000

  @FC2FCtransferoutmultiplelineitem
  Scenario: FC2FC make transferout with multiple line Item
    Given I Generate FC2FC transferout for multiple line Item
    Then I VAlidate Expected Response code with 1000

  @FC2FCordercancelinopenstate
  Scenario: FC2FC Cancel Order in open state with single line Item
    Given I am on an FC2FC Cancel Order in open state with single line Item
    Then I VAlidate epected response code With 1000

  @FC2FCOrdercancelininvoicestate
  Scenario: FC2FC Order Cancel in invoice state with Single line Item
    Given I Cancel The FC2FC invoice
    Then I VAlidate WIth The Expected Response code 1000

  @FC2FCmakeinvoicewithshortpick
  Scenario: FC2FC make invoice with shortpick
    Given I Generate The Invoice with short pick
    Then I Validate With THe Expected Response code 1000

  @FC2FCupdateshippingstatusasDelivered
  Scenario: FC2FC Update Shipping Status as delivered
    Given I Update the Shipping Status as delivered
    Then I VAlidate wIth the Expected Response 1000

  @FC2FCupdateshippingstatusin-transit
  Scenario: FC2FC Update Shipping Status as in-transit
    Given I Update The shipping status as in-transit
    Then I VAlidate With The Expected Response 1000
