# to install all dependency keep the requirements.txt and the main.py in same folder and do pip install -r requirements.txt
#Use this command to run the python file uvicorn main:app --reload --port 8081 --ws websockets
import os
import requests
import openai
import json
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from starlette.websockets import WebSocket

# Set the OpenAI API key and model
openai.api_key = ""
model = "gpt-3.5-turbo-1106"

# Define the base URL for the Java backend
JAVA_API_BASE_URL = "http://localhost:8080/api"

app = FastAPI()
last_context = {}
# Define Pydantic models for request bodies
class Author(BaseModel):
    givenName: str
    lastName: str

class Book(BaseModel):
    title: str
    genre: str
    publicationYear: int
    summary: str
    authorId: int

class Checkout(BaseModel):
    bookId: int
    checkedOutDate: str
    dueDate: str

class Message(BaseModel):
    content: str

# API Interaction Functions
def add_author(author: Author):
    """Adds a new author to the library."""
    response = requests.post(f"{JAVA_API_BASE_URL}/authors", json=author.dict())
    return response.json()

def get_all_authors():
    """Retrieves a list of all authors."""
    response = requests.get(f"{JAVA_API_BASE_URL}/authors")
    return response.json()

def add_book(book: Book):
    """Adds a new book to the library."""
    response = requests.post(f"{JAVA_API_BASE_URL}/books", json=book.dict())
    return response.json()

def get_all_books():
    """Retrieves a list of all books."""
    response = requests.get(f"{JAVA_API_BASE_URL}/books")
    print("Hello response: ",response)
    return response.json()

def checkout_book(checkout: Checkout):
    """Use this to checkout a book or when a user asks to checkout a book."""
    response = requests.post(f"{JAVA_API_BASE_URL}/checkouts", json=checkout.dict())
    return response.json()

def get_all_checkouts():
    """Retrieves a list of all book checkouts."""
    response = requests.get(f"{JAVA_API_BASE_URL}/checkouts")
    return response.json()

def search_book_by_title(title: str):
    """Searches for a book by its title."""
    response = requests.get(f"{JAVA_API_BASE_URL}/books/search", params={"title": title})
    return response.json()

def search_book_by_genre(genre: str):
    """Searches for books by a specific genre."""
    response = requests.get(f"{JAVA_API_BASE_URL}/books/searchByGenre", params={"genre": genre})
    return response.json()


def process_message(user_message_content: str):
    global last_context

    # Check for a simple follow-up response like "yes" to proceed with the last action
    if user_message_content.strip().lower() in ["yes", "proceed", "go ahead"]:
        if last_context.get("function_name") == "search_book_by_title" and last_context.get("book_id"):
            # Proceed with checkout if the last action was searching a book
            print("log :",last_context)
            checkout_response = checkout_book(Checkout(bookId=last_context["book_id"], checkedOutDate="some_date", dueDate="some_date"))
            last_context = {}  # Clear context after action
            return checkout_response
        else:
            return {"reply": "I'm not sure what to proceed with."}
    system_message = {
        "role": "system",
        "content": "You are Holmes, a powerful AI assistant designed to help users in their tasks , as the librarian holmes you are curious and use functions available to you and do not manifest anything and you help checkout books as a normal librarian does , never manipulate whenever faced with a query store the ID of the book with you so you must always be aware of the ID of the book if someone wants to checkout a book just go ahead with it if the book is available never ask for confirmation always give book ID in response also when the books are being checked out make sure the dates are always after December 2023 , checkout function gives an internal server error (never mention the internal server error in response) always say the book is not available",
    }
    user_message = {"role": "user", "content": user_message_content}

    functions_description = [
        {
            "name": "add_author",
            "description": "Add a new author to the library.",
            "parameters": {
                "type": "object",
                "properties": {
                    "givenName": {"type": "string"},
                    "lastName": {"type": "string"},
                },
                "required": ["givenName", "lastName"],
            },
        },
        {
            "name": "get_all_authors",
            "description": "Retrieve a list of all authors.",
            "parameters": {}
        },
        {
            "name": "add_book",
            "description": "Add a new book to the library.",
            "parameters": {
                "type": "object",
                "properties": {
                    "title": {"type": "string"},
                    "genre": {"type": "string"},
                    "publicationYear": {"type": "integer"},
                    "summary": {"type": "string"},
                    "authorId": {"type": "integer"},
                },
                "required": ["title", "genre", "publicationYear", "summary", "authorId"],
            },
        },
        {
            "name": "get_all_books",
            "description": "Retrieve a list of all books.",
            "parameters": {}
        },
        {
            "name": "checkout_book",
            "description": "Checkout a book from the library. Also use this function to checkout the book when the user uses word like checkout ",
            "parameters": {
                "type": "object",
                "properties": {
                    "bookId": {"type": "integer"},
                    "checkedOutDate": {"type": "string"},
                    "dueDate": {"type": "string"},
                },
                "required": ["bookId", "checkedOutDate", "dueDate"],
            },
        },
        {
            "name": "get_all_checkouts",
            "description": "Retrieve a list of all checkouts.",
            "parameters": {}
        },
        {
            "name": "search_book_by_title",
            "description": "Search for a book by title.",
            "parameters": {
                "type": "object",
                "properties": {
                    "title": {"type": "string"},
                },
                "required": ["title"],
            },
        },
        {
            "name": "search_book_by_genre",
            "description": "Search for books by genre.",
            "parameters": {
                "type": "object",
                "properties": {
                    "genre": {"type": "string"},
                },
                "required": ["genre"],
            },
        }
    ]

    try: 
        model = 'gpt-3.5-turbo-0613'
        completion = openai.ChatCompletion.create(
            model=model,
            messages=[system_message, user_message],
            functions=functions_description,
            function_call="auto",
        )

        response_message = completion.choices[0].message
        print("RESPONSE MESSAGE: ",response_message)
        if response_message.get("function_call"):
            function_name = response_message["function_call"]["name"]
            function_args = json.loads(response_message["function_call"]["arguments"])

            if function_name == "add_author":
                response = add_author(Author(**function_args))
            elif function_name == "get_all_authors":
                response = get_all_authors()
            elif function_name == "add_book":
                response = add_book(Book(**function_args))
            elif function_name == "get_all_books":
                response = get_all_books()
            elif function_name == "checkout_book":
                response = checkout_book(Checkout(**function_args))
            elif function_name == "get_all_checkouts":
                response = get_all_checkouts()
            elif function_name == "search_book_by_title":
                response = search_book_by_title(function_args["title"])
            elif function_name == "search_book_by_genre":
                response = search_book_by_genre(function_args["genre"])
            else:
                return {"error": "Command not recognized or not implemented"}

            # Format the response into a natural language summary if it's a JSON response
            response_json = json.dumps(response)
            print("Debug json : ",response_json)
            # Create a new system message to interpret the backend response
            interpret_message = {
                "role": "system",
                "content": f"Interpret this JSON response in a human-readable format as you would just give the response in natural language answering the query asked : {response_json}"
            }

            # Send the new message for interpretation
            completion = openai.ChatCompletion.create(
                model=model,
                messages=[system_message, user_message, interpret_message]
            )
            print("Completion DEBUG",completion)
            # Return the AI's interpretation of the JSON response
            interpreted_response = completion.choices[0].message
            return {"reply":interpreted_response["content"]}

        else:
            # For general conversational responses, return the AI's response
            return {"reply":response_message["content"]}
    except Exception as e:
        print(f"An error occurred: {e}")
        return {"error": str(e)}

    return {"error": "Command not recognized or not implemented"}


@app.post("/process_message")
async def process(message : Message):
    # print("Received message:", message.dict())  # Add this line to print the request body

    print("Debug the message: ",message)
    response = process_message(message.content)
    # response = {"message":"HI"}
    return  response

# Main method to run the application
if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8081, debug=True)