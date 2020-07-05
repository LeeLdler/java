#include<stdio.h>
#include<string.h>

void fuzhi(char a[],int n,char b[])
{
    int k=0,i;
    for(i=n-1;i<strlen(a);i++)
    {
        b[k]=a[i];
        k++;
    }
    b[k]='\0';
}


int main()
{
    int n;
    char b[1000],a[1000];
    scanf("%s",a);
    scanf("%d",&n);
    fuzhi(a,n,b);
    printf("%s",b);
    return 0;
}

