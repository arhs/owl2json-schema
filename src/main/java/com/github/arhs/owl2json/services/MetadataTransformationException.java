/* ----------------------------------------------------------------------------
 *          PROJECT : CELLAR maintenance
 *
 *          PACKAGE : eu.europa.publications.rdfedit.service
 *             FILE : MetadataTransformationException.java
 *
 *       CREATED BY : ARHS Developments
 *               ON : 09 16, 2017
 *
 *      MODIFIED BY : ARHS Developments
 *               ON : $LastChangedDate: 2017-09-16 22:16:32 +0200 $
 *          VERSION : $LastChangedRevision: 0 $
 *
 * ----------------------------------------------------------------------------
 * Copyright (c) 2011-2017 European Commission - Publications Office
 * ----------------------------------------------------------------------------
 */
package com.github.arhs.owl2json.services;

/**
 * @author ARHS Developments
 */
public class MetadataTransformationException extends RuntimeException {
    public MetadataTransformationException() {
    }

    public MetadataTransformationException(String message) {
        super(message);
    }

    public MetadataTransformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataTransformationException(Throwable cause) {
        super(cause);
    }

    public MetadataTransformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
