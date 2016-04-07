make_malloc: mymalloc.c mymalloc.h malloc_test
	gcc -fPIC -c -m32 mymalloc.c
	ld -shared -soname mymalloc.so.1 -o mymalloc.so.1.0 -lc -melf_i386 mymalloc.o
	ln -s mymalloc.so.1.0 mymalloc.so
	export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH
	gcc -rdynamic -o malloc_test malloc_test.c -ldl -m32 -g