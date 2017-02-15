# Dynamic Links Catalog

This project arose from the subtle need to have a directory-listing of available services on my home server. I wasn't able to find another project where it was easy to have a good looking, and good functioning list of items, that was easily updateable.

This project intends to create a simpler, more efficient way to create an interactive list of services and documents using a single configuration file.

The goals of the project are as follows:
- Support links to documents (docx/pdf)
- Support links to services
- Use React's component based architecture to easily add child components dynamically
- Use Spring Boot's 'magic' qualities to develop a simple server-side configuration service
- Have a single configuration file editable within the source code to dynamically generate links page

Future goals:
- Allow configuration file to be edited from the web interface using account-based authentication
- Have multiple themes for the style of the page
- Allow between multiple types of structures (ex. Card, Hero, etc.)