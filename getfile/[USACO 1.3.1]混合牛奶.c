#include<stdio.h>
int main()

{
    int n,m,a[1000][2],t,i,j,c,r,z;
    while(~scanf("%d %d",&c,&n))
    {
        r=0,z=0;
        for(i=0; i<n; i++)
            for(j=0; j<2; j++)
                scanf("%d",&a[i][j]);
        for(i=0; i<n-1; i++)
        {
            for(j=0; j<n-i-1; j++)
            {
                if(a[j][0]>a[j+1][0])
                {
                    m=a[j][0];
                    a[j][0]=a[j+1][0];
                    a[j+1][0]=m;
                    t=a[j][1];
                    a[j][1]=a[j+1][1];
                    a[j+1][1]=t;
                }
            }
        }
        for(i=0; i<n; i++)
        {
            r=a[i][1]+r;
            z=a[i][1]*a[i][0]+z;
            if(r>c)
            {
                z=z-(r-c)*a[i][0];
                r=c;
            }
            if(r==c)
                break;
        }
        printf("%d",z);
    }
}
