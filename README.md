[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cfa1ceff322a4b9391889256a906defe)](https://www.codacy.com/app/nameless-cat/votemeal?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nameless-cat/votemeal&amp;utm_campaign=Badge_Grade)

## Simple lunch voting system

REST API on stack Hibernate/Spring/SpringMVC **without frontend**.

Voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

### API

| Main point | Aspects | Use | Explanation |
|------------|---------|---------|-------------|
| profile    | `view`  | `get: /profile/USER_ID` | *Access user profile* |
|            | `edit`  | `put: /profile/USER_ID` | *...and edit*|
|            | `history` | `get: /profile/USER_ID/hitory`| *Shows user vote history*|
| register   | `new user` | `post: /register` | *Register new user* |
| vote       | `vote` | `post: /vote/CAFE_ID` | *Voting for cafe with id* |
|            | `nevermind` | `delete: /vote` | *Remove vote* |
|            | `view`  | `get: /vote` | *Get cafe list for today* |
|            | `lunches` | `get: /vote/CAFE_ID/lunches` | *Get lunch list for cafe* |
| admin      | `all cafe` | `get: /admin/restaurants` | *Get all cafe* |
|            | `one cafe` | `get: /admin/restaurants/CAFE_ID` | *Get one cafe* |
|            | `add cafe` | `post: /admin/restaurants` | *Create new cafe* |
|            | `edit cafe`| `put: /admin/restaurants/CAFE_ID` | *Edit cafe* |
|            | `get vote` | `get: /admin/vote` | *Show vote list for today* |
|            | `get vote at`| `get: /admin/vote/DATE` | *Show vote list at date* |
|            | `add to vote`| `put: /admin/vote` | *Adding cafe list to vote for today* |
|            | `remove from vote` | `delete: /admin/vote/CAFE_ID` | *Removing cafe from vote* |
|            | `add lunch` | `post: /admin/restaurants/CAFE_ID/lunches` | *Add lunch to cafe menu* |
|            | `lunches` | `get: /admin/restaurants/CAFE_ID/lunches` | *Show cafe menu* |
|            | `one lunch` | `get: /admin/restaurants/CAFE_ID/lunches/LUNCH_ID`| *Show one menu row* |
|            | `edit lunch`| `put: /admin/restaurants/CAFE_ID/lunches/LUNCH_ID`| *Edit one menu row* |
|            | `delete lunch` | `delete: /admin/restaurants/CAFE_ID/lunches/LUNCH_ID` | *Delete one menu row* |