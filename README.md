# Pass-in - NLW 2024

## Description

This API is a project developed during the Next Level Week 2024, an event organized by Rocketseat.
The project is a REST API that allows managing attendees into events.

The architecture is based on controllers, services and repositories, and the database used is HyperSQL with based-disk
persistence. The domain model is composed of two entities: `Event` and `Attendee`, and their classes are located in the
`domain` package.

## Endpoints

The API has the following endpoints:

- `GET /attendees/:attendeeId/badge`: Get the badge of an attendee
- `GET /events/:eventId`: Get details of a specific event
- `GET /events/attendees/:eventId`: Get all attendees of a specific event
- `POST /events`: Create a new event
- `POST /events/:eventId/attendees`: Add an attendee to a specific event
- `POST /attendees/:attendeeId/check-in`: Check-in an attendee

## Prerequisites

- Java 17;
- IDE (IntelliJ IDEA, Eclipse, etc);
- Postman (or any other API client).
