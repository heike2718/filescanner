# filescanner

## What is it?

This is a REST-API that scans files with ClamAV and determines the MIME-Type of the file in the first place.

In order to scan a file with the clamavd daemon this app uses [solita/clamav-client](https://github.com/solita/clamav-java).

In case of application/zip it checks if this could be a zip-bomb too, since this ist not being detected as virus by ClamAV.

If it is configured like this, in all cases - unauthorized clients, virus other vulterable threads -  a message is send both to a Telegram chat and a mail account.

## Who is allowed to use it?

Authorized client applications are allowed to use it. The clients are checked by their clientId (server to server).

## Required environment

There shoud be a clamav installation at the server as for instance [mko-x/docker-clamav](https://github.com/mko-x/docker-clamav).

## Usage as library

In addition to setting this up as microservice, the filescanner-service can be used as jar-dependency as is in order to avoid RESTClient communication overhead.

## API

The ClamAVService provides 2 methods:

* checkAlive: returns true when the VirusScanner can be reached. This method can be used for HealthChecks.

* scanFile: performs a virus check of a ScanRequestPayload


## Releasenotes

[Release-Notes](RELEASE-NOTES.md)
