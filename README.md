![Logo](https://i.imgur.com/D39EiY9.png)
# Recipe sharing webapp

This web application made for **Web application development** subject.

## Members

#### Frontend subteam (HTML, CSS, React)
- [@neczemate](https://www.github.com/neczemate)
- [@vzsolt23](https://www.github.com/VZsolt23)
#### Backend subteam (Java, Spring Boot, PostgreSQL)
- [@davidpokol](https://www.github.com/davidpokol)
- [@ferencorgovan](https://www.github.com/ferencorgovan)
## Features
**The user can:**
- sign up
- log in
- add recipes
- update/delete recipes they have added
- search for recipes with a string(ingredient/part of the making)
- filter recipes by category

All CRUD operations are linked with the database.

---
#### **Meal** categories (enums):
🥐 BREAKFAST

🥞 BRUNCH

🕚 ELEVENSES

🍖 LUNCH

🍵 TEA

🧆 SUPPER

🍽 DINNER

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`POSTGRES_DB_URL`

`POSTGRES_DB_USER`

`POSTGRES_DB_PASSWORD`

`JWT_SECRET`


## Run Locally

Clone the project

```bash
git clone https://github.com/davidpokol/webapp.git gasztrokucko
```

Go to the frontend directory

```bash
cd gasztrokucko/frontend
```

Install dependencies

```bash
yarn install
```

Start the React application

```bash
yarn start
```
Go to the backend directory


```bash
cd ../backend
```

Start the server
```bash
mvn spring-boot:run
```


## Screenshots

![Recipe example](https://i.imgur.com/50RUUCl.png)

## Color Reference

| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Navigation Bar | ![#3E3E3E](https://via.placeholder.com/10/3E3E3E?text=+) #3E3E3E |
| Buttons, Strokes | ![#F4722B](https://via.placeholder.com/10/F4722B?text=+) #F4722B |
| Background | ![#F6E7C1](https://via.placeholder.com/10/F6E7C1?text=+) #F6E7C1 |


---

###### 2023 - Group F