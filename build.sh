#!/bin/bash -e

pushd model
mvn clean
mvn install
popd

pushd dataprovider
mvn clean
mvn install
popd

pushd logic
mvn clean
mvn install
popd

pushd server
mvn clean
mvn install
mvn assembly:assembly
popd
