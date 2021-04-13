# REST API Dokumentation

#**Get Report**

---
Returns json data about all workingstimes for a user.

* **URL**

  /v1/reports

* **Method:**

  `GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `[
  {
  "id": 1,
  "date": "2020-11-25",
  "month": 11,
  "startTimeHours": 8,
  "startTimeMinutes": 0,
  "stopTimeHours": 15,
  "stopTimeMinutes": 30,
  "durationPauseHours": 1,
  "durationPauseMinutes": 0,
  "workingHours": "06:30"
  } ...
  ]`

* **Error Response:**

* **Code:** 404 Not Found <br />
  **Content:** `Not Found`

* **Sample Call:**

  ```http://localhost:9002/v1/reports```

#**Get Report per day**

---
Returns json data about a specific working day.

* **URL**

  /v1/reportByDay

* **Method:**

  `GET`

* **URL Params**

  `date=2020-11-12`

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `{
  "id": 1102,
  "date": "2021-02-01",
  "month": 2,
  "startTimeHours": 8,
  "startTimeMinutes": 0,
  "stopTimeHours": 16,
  "stopTimeMinutes": 30,
  "durationPauseHours": 1,
  "durationPauseMinutes": 0,
  "workingHours": "07:30"
  }`

* **Error Response:**

* **Code:** 400 Bad Request <br />
  **Content:** `Bad Request`

* **Sample Call:**

  ```http://localhost:9002/v1/reportByDay/?date=2021-16-01```


#**Get Report per month**

---
Returns json data about a specific working month.

* **URL**

  /v1/reportByDay

* **Method:**

  `GET`

* **URL Params**

  `month=11`

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `[
  {
  "id": 1,
  "date": "2020-11-25",
  "month": 11,
  "startTimeHours": 8,
  "startTimeMinutes": 0,
  "stopTimeHours": 15,
  "stopTimeMinutes": 30,
  "durationPauseHours": 1,
  "durationPauseMinutes": 0,
  "workingHours": "06:30"
  },
  {
  "id": 2,
  "date": "2020-11-26",
  "month": 11,
  "startTimeHours": 8,
  "startTimeMinutes": 0,
  "stopTimeHours": 15,
  "stopTimeMinutes": 45,
  "durationPauseHours": 1,
  "durationPauseMinutes": 0,
  "workingHours": "06:45"
  }
  ]`

* **Error Response:**

* **Code:** 400 Bad Request <br />
  **Content:** `Bad Request`

* **Sample Call:**

  ```http://localhost:9002/v1/reportByMonth/?month=11```

#**Get Report per month**

---
Returns json data about a specific working month.

* **URL**

  /v1/sumByMonth

* **Method:**

  `GET`

* **URL Params**

  `month=11`

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `144: 15`

* **Error Response:**

* **Code:** 400 Bad Request <br />
  **Content:** `Bad Request`

* **Sample Call:**

  ```http://localhost:9002/v1/sumByMonth/?month=11```

#**Get Workingsdays per month**

---
Returns json data about a specific month and the amount of working days from germany.

* **URL**

  /v1/workdaysByMonth

* **Method:**

  `GET`

* **URL Params**

  * `year=2020`    
  * `month=11`

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `21`

* **Error Response:**

* **Code:** 400 Bad Request <br />
  **Content:** `Bad Request`

* **Sample Call:**

  ```http://localhost:9002/v1/workdaysByMonth?year=2020&month=11```

#**Pausenmanagement**

---
Controlles the Start and Stop of the pause timer.

* **URL**

  * /v1/startPause - POST
  * /v1/stopPause - POST
  * /v1/pausetime - GET

* **Method:**

  `POST`,`GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** 
   /pausestart
   /pausestop
  
OR

**Content:**

  3 ... Time in Minutes

* **Error Response:**

* **Code:** 404 Not Found <br />
  **Content:** `Not Found`
  
OR

* **Code:** 505 Internal Server Error <br />
  **Content:** `Internal Server Error`

* **Sample Call:**

  ```http://localhost:9002/v1/startPause```
  
  ```http://localhost:9002/v1/stopPause```

  ```http://localhost:9002/v1/pausetime```


#**Application Infos**

---
Returns data of the application.

* **URL**

  /v1/info

* **Method:**

  `GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `[Application info]
  Application name : arbeitszeiterfassung
  Build version    : 1.0-SNAPSHOT
  Build timestamp  : 2021-04-08 08:08`

* **Error Response:**

* **Code:** 404 Not Found <br />
  **Content:** `Not Found`

* **Sample Call:**

  ```http://localhost:9002/v1/info```

#**User Informations**

---
Returns data of user like name and workingshours per weeks.

* **URL**

  /v1/employeeData

* **Method:**

  `GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

* **Code:** 200 <br />
  **Content:** `[
  {
  "id": 2701,
  "firstName": "Dalli",
  "lastName": "Karl",
  "weeklyWorkinghours": 35,
  "maxDailyWorkinghours": 10
  }
  ]`

* **Error Response:**

* **Code:** 404 Not Found <br />
  **Content:** `Not Found`

* **Sample Call:**

  ```http://localhost:9002/v1/employeeData```
