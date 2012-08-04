#!/bin/bash

# framework ID for deploying to Cloudbees is bees
play bees:app:deploy --%bees

#play bees:app:deploy --%bees -Dhttp.proxyHost=192.168.192.1 -Dhttp.proxyPort=3128
