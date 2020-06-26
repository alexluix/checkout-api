# Code challenge: Checkout API

![CI](https://github.com/landpro/checkout-api/workflows/CI/badge.svg)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

## Task

We would like you to build a simplified e-commerce API with a single endpoint that performs a
checkout action. The single endpoint should take a list of watches and return the total cost.
In terms of programming language, we work with Kotlin and Java 8+, if you feel that you have the
experience to build a solution in any of those languages then please do. Otherwise, we are happy for
you to build a solution using a language and framework that you feel best showcases your ability.

| Watch ID | Watch Name   | Unit Price | Discount  |
|----------|--------------|------------|-----------|
| 001      | Rolex        | 100        | 3 for 200 |
| 002      | Michael Kors | 80         | 2 for 120 |
| 003      | Swatch       | 50         |           |
| 004      | Casio        | 30         |           |

There are a few requirements worth noting here:
- The first two products have a possible discount. As an example, if the user attempts to
checkout three or six Rolex watches then they will receive the discount price once or twice,
respectively.
- There is no limit to the number of items or combinations of watches a user can checkout.
- There is no limit to the number of times a discount can be used.
- Similarly, a user can checkout a single item if they wish.

## Assumptions Made

- Price currency doesn't have cents — e.g. `JPY`
- Total checkout price can't go beyond 2bi as well as watch prices

## API

Endpoint
`POST http://localhost:8080/checkout`

Request
```text
# Headers
Accept: application/json
Content-Type: application/json

# Body
[
"001",
"002",
"001",
"004",
"003"
]
```

Response
```text
# Headers
Content-Type: application/json

# Body
{ "price": 360 }
```

## Tech Stack

- Kotlin
- Maven
- jUnit
- Mockito

## Usage

- Build & test `./mvnw clean verify`
- Fix kt-lint issues `./mvnw ktlint:format`
- Run `./mvnw spring-boot:run`
- Sample API request to `localhost`:
```shell
curl -X "POST" "http://localhost:8080/checkout" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'[
  "001",
  "002",
  "003"
]'
```
