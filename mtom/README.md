# MTOM

Provert ruzne stavy pro:

```
<xs:element name="ResponseHolder" type="xs:base64Binary"/>
<xs:element name="ResponseHolder" type="xs:base64Binary" xmime:expectedContentTypes="application/xml"/>
<xs:element name="ResponseHolder" type="xs:base64Binary" xmime:expectedContentTypes="application/octet-stream"/>
```

Dale prover anotaci: <code>com.sun.xml.ws.developer.StreamingAttachment</code>

## Zdroje
- https://dzone.com/articles/use-mtom-to-efficiently-transmit-binary-content-in
- https://access.redhat.com/documentation/en-us/red_hat_fuse/7.5/html/apache_cxf_development_guide/fusecxfswa
- https://access.redhat.com/documentation/en-us/red_hat_fuse/7.5/html/apache_cxf_development_guide/fusecxfmtom
- https://docs.oracle.com/cd/E14571_01/web.1111/e13734/mtom.htm#WSADV143
