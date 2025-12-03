# Solution Steps

1. Define two DTOs: ProductAdminDTO (with all fields) and ProductUserDTO (with only public fields).

2. Adjust Product entity if needed to clearly separate sensitive and public fields.

3. In ProductRepository, add custom queries (e.g., findAllWithSupplierDetails) for admin/sensitive fetches, and use regular findAll for non-admin users.

4. Implement ProductService with getAllProducts and getProductById methods, returning either DTO type depending on the calling user's role. Use SecurityContextHolder for role detection. Map only needed fields, and avoid fetching heavy/sensitive info for regular users.

5. Adjust ProductController so it always calls ProductService and returns List<?> (typed as DTO), preserving endpoint signature.

6. Create NotFoundException, ApiError, and a robust GlobalExceptionHandler to ensure structured error output for 404, 403, validation etc.

7. Refactor performance (in ProductRepository), e.g. using optimized queries to only load what is required for each case.

8. Write integration tests for /api/products: ensure admins see cost price & supplier details, users do not; verify security and error handling.

9. Organize the code using clean package structure and best Java/Spring naming and logging practices. Ensure future extensibility (e.g., for versioning or further field splits) via clear DTO mapping in one place (ProductService).

