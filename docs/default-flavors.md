# instance flavor sku's
The general compute flavors are based on deic's type1 descriptions available [somewhere](somewhere). 

GPU instances are based on our current hardware, machines with six t4 GPUs, 256GB memory, and 64 vCPUs (2x16 core EPYCs). I have cut these machines into six slices minus some to make room for the host.

I chose the storage based on whatever we did on our old OpenStack platform.

And last, the pricing is something I just came up with...

Name | vCPU | RAM | Volatile Storage | GPU | Price 
--- | --- | --- | --- | --- | --- 
aau-general-small | 4 vCPU | 16 Gb | 1 Tb | N/A | 0.34 DKK/hour
aau-general-medium | 8 vCPU | 32 Gb | 1 Tb | N/A | 0.68 DKK/hour
aau-general-large | 16 vCPU | 64 Gb | 1 Tb | N/A | 1.37 DKK/hour
aau-general-xlarge | 64 vCPU | 256 Gb | 1 Tb | N/A | 5.50 DKK/hour
aau-t4-1 | 10 vCPU | 40 Gb | 1 Tb | T4 16 Gb | 8.50 DKK/hour
aau-t4-2 | 20 vCPU | 80 Gb | 1 Tb | 2x T4 16 Gb | 17.00 DKK/hour
aau-t4-3 | 30 vCPU | 120 Gb | 1 Tb | 3x T4 16 Gb | 25.50 DKK/hour
aau-t4-4 | 40 vCPU | 160 Gb | 1 Tb | 4x T4 16 Gb | 34.00 DKK/hour
aau-t4-5 | 50 vCPU | 200 Gb | 1 Tb | 5x T4 16 Gb | 42.50 DKK/hour
aau-t4-6 | 60 vCPU | 240 Gb | 1 Tb | 6x T4 16 Gb | 51.00 DKK/hour

