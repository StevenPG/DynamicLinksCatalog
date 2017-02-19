# Dynamic Links Catalog

This project arose from the subtle need to have a directory-listing of available services on my home server. I wasn't able to find another project where it was easy to have a good looking, and good functioning list of items, that was easily updateable.

This project intends to create a simpler, more efficient way to create an interactive list of services and documents using a single configuration file.

### This project follows the guidelines laid out in Semantic Versioning 2.0.0
http://semver.org/
Given a version number MAJOR.MINOR.PATCH, increment the:

MAJOR version when you make incompatible API changes,

MINOR version when you add functionality in a backwards-compatible manner, and

PATCH version when you make backwards-compatible bug fixes.

Additional labels for pre-release and build metadata are available as extensions to the MAJOR.MINOR.PATCH format.

### The goals of the project are as follows:
- Support links to documents (docx/pdf)
- Support links to services
- Use React's component based architecture to easily add child components dynamically
- Use Spring Boot's 'magic' qualities to develop a simple server-side configuration service
- Have a single configuration file editable within the source code to dynamically generate links page