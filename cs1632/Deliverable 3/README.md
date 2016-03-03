##User Story 1
As a NASIOC member<br>
I want to log in<br>
So that I can start internet fights

1.	Given that I am on the home page<br>
    And I am not logged in<br>
	When I try to log in with a valid username and an invalid password<br>
	Then I should receive an "invalid password" error page

2.	Given that I am on the home page<br>
		And I am not logged in<br>
	When I try to log in with a valid username and an invalid password<br>
	Then I should receive an error page with a reset password link
	
3.	Given that I am on the home page<br>
		And I am not logged in<br>
	When I try to log in with no username and no password<br>
	Then I should receive an "invalid password" error page
	 
4.	Given that I am on the home page<br>
		And I am not logged in<br>
	When I click the "Forgot Password" link in the footer<br>
	Then I should see a form to allow me to reset my password
	
5.	Given that I am on the home page<br>
		And I am not logged in<br>
	When I view the "Log in" section<br>
	Then I should see a "Remember Me?" check box
	
##User Story 2
As a stranger to NASIOC<br>
I want information about this forum<br>
So that I can learn what this site is all about

1.	Given that I am on the home page<br>
	When I view the title<br>
	Then I should see that it contains "North American Subaru Impreza Owners Club"
	
2.	Given that I am on the home page<br>
	When I see the "Site Navigation" sidebar<br>
	Then I should see links for "About", "Rules", and "FAQ" sections
	
3.	Given that I am on the home page<br>
	When I click the "Privacy" link in the footer<br>
	Then I should be redirected to the "Privacy Policy" page
	
4.	Given that I am on the home page<br>
	When I view the "Log in" section<br>
	Then I should see that it contains the phrase "Not a member yet?"
	
5.	Given that I am on the home page<br>
	When I click the "Register Now!" link in the Log in section<br>
	Then I should be redirected to the forum rules page
	
6.	Given that I am on the home page<br>
	When I view the headers<br>
	Then I should see today's date
	
##User Story 3
As a regular user of NASIOC<br>
I want to see forum links organized in different ways<br>
So that I can decide which sections to visit

1.	Given that I am on the main forums page<br>
		And I am not logged in<br>
	When I view the welcome banner<br>
	Then I should see a message that I am "currently viewing our forum as a guest"

2.	Given that I am on the main forums page<br>
	Then I should see different NASIOC subforums categorized as "General", "Technical", "Classifieds", and "Chapters"

3.	Given that I am on the main forums page<br>
	When I view the "NASIOC General" section<br>
	Then I should see links for "General Community", "Motorsports", "Member's Car Gallery", and "News & Rumors" subforums
	
4.	Given that I am on the main forums page<br>
	When I click the "Search" link in the navigation bar<br>
	Then I should be redirected to a page with a form to search with
	
5.	Given that I am on the main forums page<br>
	When I view the "NASIOC Chapters" section<br>
	Then I should see icons denoting if a new post has been made
	
##User Story 4
As a Subaru owner<br>
I want to browse private classifieds<br>
So that I can buy, sell, or trade car parts

1.	Given that I am on the "Complete Car Part-Out" page<br>
	When I view the title<br>
	Then I should see that it contains "Complete Car Part-Out"

2.	Given that I am on the "Complete Car Part-Out" page<br>
	When I view the "Threads in Forum" section<br>
	Then I should see a link to a "Part-Out Forum Guidelines" sticky (a "permanent" thread)
	
3.	Given that I am on the "Complete Car Part-Out" page<br>
	When I view the forum table headers<br>
	Then I should see that it is separated by "Thread / Thread Starter", "Last Post", "Replies", and "Views"
	
4.	Given that I am on the "Complete Car Part-Out" page<br>
		And I view the "Display Options" section<br>
	When I click the "Prefix" drop menu<br>
	Then I should see options for "(any prefix)", "(no prefix)", "FS: (For Sale)", or "FS/FT: (For Sale or Trade)"
	
5.	Given that I am on the "Complete Car Part-Out" page<br>
	When I view the "Threads in Forum" section<br>
	Then I should see an button (as an image) to start a new thread
	
##User Story 5
As a Subaru owner<br>
I want to research OEM and aftermarket parts<br>
So that I find parts vendors and distributors

1.	Given that I am on the "NASIOC dBase" page<br>
	When I view the title<br>
	Then I should see that it contains "NASIOC Products dBase"
	
2.	Given that I am on the "NASIOC dBase" page<br>
	Then I should see links for "Brakes", "Drivetrain", "Engine", "Interior", and "Wheels"
	
3.	Given that I am on the "NASIOC dBase" page<br>
	When I view the search bar<br>
	Then I should see radio buttons for "Web" and "NASIOC.com"
	
4.	Given that I am on the "NASIOC dBase" page<br>
	When I search for "Advan" in the search bar<br>
	Then I should see the term "Advan" appear in the search results
	
5.	Given that I am on the "NASIOC dBase" page<br>
	When I click the link for the "Tires" category<br>
	Then I should be redirected to the "Tires" page
	
6.	Given that I am on the "NASIOC dBase" page<br>
	When I click the link for the "Suspension & Handling" category<br>
	Then I should see a "Return to Main Listing" link in the resulting page
