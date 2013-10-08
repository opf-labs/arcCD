#!/bin/bash

command -v cdrdao >/dev/null 2>&1 || {
    exit 1; 
}

exit 0;