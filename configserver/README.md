```text
http://localhost:8071/accounts/default
```
![img.png](img.png)
```text
http://localhost:8071/accounts/prod
```
![img_1.png](img_1.png)
```text
http://localhost:8071/accounts/qa
```
![img_2.png](img_2.png)


to encrypt a value from plain text to encrypted text.
use the post man to convert plain text to encrypted text.
![img_3.png](img_3.png)

Make sure use: {cipher}
![img_4.png](img_4.png)

When it is trying to send these properties to the actual microservice, it is going to decrypt and send the values in a plain text value.

Test decrypted:
![img_5.png](img_5.png)