'use client'
import { useRouter } from 'next/navigation';
import { BookFormData } from './biblioteca/add-book-modal'
import { setData } from '@/core/lStorage';
import { Book } from './biblioteca/book-list';

interface Props {
  books: Book[];
}

export function SeusLivros({ books }: Props) {
  const router = useRouter();

  return (
    <div className="mt-6 sm:mt-8">
      <h2 className="text-lg sm:text-xl font-semibold mb-3 sm:mb-4 text-black">Seus Livros</h2>

      <div className="grid grid-cols-2 sm:grid-cols-2 lg:grid-cols-4 gap-3 sm:gap-4 cursor-pointer">
        {books.map((book, index) => (
          <div
            key={index}
            onClick={() => {
              setData('livro', book.id);
              router.push(`/livro`);
            }}
            className="bg-white rounded-xl sm:rounded-2xl p-3 sm:p-4 shadow-sm flex flex-col hover:shadow-md transition-shadow duration-200"
          >
            {/* Imagem real */}
            <div className="rounded-lg h-28 sm:h-32 lg:h-36 relative mb-2 sm:mb-3 overflow-hidden">
              <img
                src={book.capa}
                alt={`Capa do livro ${book.titulo}`}
                className="object-cover"
                sizes="(max-width: 640px) 50vw, (max-width: 1024px) 25vw, 20vw"
              />
            </div>

            <div className="flex-1">
              <h3 className="font-semibold text-gray-900 text-xs sm:text-sm mb-1 cursor-text">
                {book.titulo}
              </h3>
              <p className="text-gray-600 text-[10px] sm:text-xs cursor-text">
                por {book.autor}
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}