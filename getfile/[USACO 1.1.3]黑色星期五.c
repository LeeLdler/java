#include<stdio.h>
int runnian(int n)
{
    if((n%100!=0&&n%4==0)||n%400==0)
       return 1;
    else
    return 0;
}
int main()
{
    int m,i,j,k,t;
    int a[7]={0};
    scanf("%d",&m);
    k=-18;
    j=0;
    while(j<m)
    {
        k=k+31;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        if(runnian(j+1900)==1)
            k=k+29;
        else
            k=k+28;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        k=k+30;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        k=k+30;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        k=k+30;
        a[k%7]++;
        k=k+31;
        a[k%7]++;
        k=k+30;
        a[k%7]++;
        j++;
    }
    printf("%d %d %d %d %d %d %d",a[6],a[0],a[1],a[2],a[3],a[4],a[5]);
    return 0;
}
