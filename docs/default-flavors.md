# instance flavor sku's
The general compute flavors are based on deic's type1 descriptions available [somewhere](somewhere). 

GPU instances are based on our current hardware, machines with six t4 GPUs, 256GB memory, and 64 vCPUs (2x16 core EPYCs). I have cut these machines into six slices minus some to make room for the host.

I chose the storage based on whatever we did on our old OpenStack platform.

And last, the pricing is something I just came up with...

Name | vCPU | RAM | Volatile Storage | GPU | Price 
--- | --- | --- | --- | --- | --- 
uc-general-small | 4 vCPU | 16 Gb | 1 Tb | N/A | 0.34 DKK/hour
uc-general-medium | 8 vCPU | 32 Gb | 1 Tb | N/A | 0.68 DKK/hour
uc-general-large | 16 vCPU | 64 Gb | 1 Tb | N/A | 1.37 DKK/hour
uc-general-xlarge | 64 vCPU | 256 Gb | 1 Tb | N/A | 5.50 DKK/hour
uc-t4-1 | 10 vCPU | 40 Gb | 1 Tb | T4 16 Gb | 8.50 DKK/hour
