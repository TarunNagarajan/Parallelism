#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>

#define ARRAY_SIZE 10000000

/* 
PROBLEM STATEMENT
Problem Statement:

Write a C++ program to compute the sum of all elements in an integer array of size 100,000 using both:

Serial computation.

Parallel computation using OpenMP.

Use an OpenMP parallel loop with a reduction clause to avoid race conditions in the parallel version.

Tasks:

Initialize the array with arbitrary integers (you can fill with rand() or a fixed pattern like all 1s).

Compute and print the serial sum and its execution time.

Compute and print the parallel sum and its execution time.

Verify that both results match.

Calculate and display the speedup: the ratio between serial time and parallel time. 
​
Bonus:
Try larger array sizes (like 1 million, 10 million, etc.) and observe the performance trends.


*/

// Function for serial sum
long long serial_sum(int arr[], int size) {
    long long sum = 0;
    for (int i = 0; i < size; i++) {
        sum += arr[i];
    }
    return sum;
}

// Function for parallel sum using OpenMP
long long parallel_sum(int arr[], int size) {
    long long sum = 0;
    
    #pragma omp parallel for reduction(+:sum)
    for (int i = 0; i < size; i++) {
        sum += arr[i];
    }
    
    return sum;
}

int main() {
    int* array = (int*)malloc(ARRAY_SIZE * sizeof(int));

    srand(time(NULL));
    for (int i = 0; i < ARRAY_SIZE; i++) {
        array[i] = rand() % 100; 
        // Random values between 0 and 99
    }
    
    double start_time = omp_get_wtime();
    long long serial_result = serial_sum(array, ARRAY_SIZE);
    double serial_time = omp_get_wtime() - start_time;

    start_time = omp_get_wtime();
    long long parallel_result = parallel_sum(array, ARRAY_SIZE);
    double parallel_time = omp_get_wtime() - start_time;

    printf("Array size: %d\n", ARRAY_SIZE);
    printf("Serial sum: %lld, Time: %.6f seconds\n", serial_result, serial_time);
    printf("Parallel sum: %lld, Time: %.6f seconds\n", parallel_result, parallel_time);
    printf("Speedup: %.2f times\n", serial_time / parallel_time);
    
    // Verify both results match
    if (serial_result == parallel_result) {
        printf("Results match - Correct sum calculated.\n");
    } else {
        printf("ERROR: Results don't match.\n");
    }

    /* 
    RESULTS: 
    Array size: 10000000
    Serial sum: 494578282, Time: 0.015180 seconds
    Parallel sum: 494578282, Time: 0.005201 seconds
    Speedup: 2.92 times
    Results match - Correct sum calculated.
    */
    
    free(array);
    return 0;
}
