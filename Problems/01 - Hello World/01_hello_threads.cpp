#include <iostream>
#include <omp.h>

int main() {

    omp_set_num_threads(4);  

    // Begin parallel region
    #pragma omp parallel
    {
        // Get the thread ID (0-based) and total thread count
        int thread_id = omp_get_thread_num();
        int total_threads = omp_get_num_threads();

        // Ensure threads print one at a time to avoid interleaving
        #pragma omp critical
        {
            std::cout << "Hello from thread " << thread_id
                      << " out of " << total_threads << std::endl;
        }
    }

    return 0;
}
