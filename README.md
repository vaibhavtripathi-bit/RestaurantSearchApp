# RestaurantSearchApp

Focus of this demo project is on :
1. Code should be scalable, clean, easy to understand
and new changes can be handled easily.
2. Different functionality/Modules should be seperated properly so
that we can test them easily and if required can replace them with
new implementation.

This Demo required many improvements :

1. Pagination is not handled, handling data using diffutil for recycle view
2. Handling error case and no data cases to show proper message in
UI and may be retry button.
3. Two way data binding can be used for populating UI
and handled visibility of the views, and triggering actions
if required.
4. API call are mocked with local data.
5. Detail Page or some better UX design.

Assumption 
1. Search with empty string means API should return all the data.
