Feature: To ZipERP new order creation
 ------------
 
 	@zipERPordersinglelineitem
  Scenario: ZipERP Create Order with single line Item
    Given I am on an ZipERP order Create API with single line Item
    Then I VAlidate ZipERP order response code with 1000
    
    
    @ZipERPordercancelinopenstate
    Scenario: ZipERP order cancel in open state
   	Given I am on an ZipERP order cancel in open state
   	Then I validate ZipERP order cancel response code with 1000
    
  @ZipERPgeneratepicklist
  Scenario: ZipERP generate picklist with single line Item
    Given I generate ZipERP Picklist sinlge item
    Then I VAlidate ExPected response code with 1000
    
  @ZipERPgenerateunpicklist
  Scenario: ZipERP Order Cancel in picking state
    Given I generate ZipERP unpicklist
    Then I Validate ZipERP unpicklist Response 1000