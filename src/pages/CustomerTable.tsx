import { useEffect, useState } from 'react';

interface Customer {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

function CustomerTable() {
  const [page, setPage] = useState<Page<Customer>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
    number: 0,
    size: 10,
  });
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [sort, setSort] = useState('lastName,asc');

  const fetchCustomers = async () => {
    setLoading(true);
    const res = await fetch(
      `http://localhost:8080/api/customers?page=${currentPage}&size=10&sort=${sort}`
    );
    const data: Page<Customer> = await res.json();
    setPage(data);
    setLoading(false);
  };

  useEffect(() => {
    fetchCustomers();
  }, [currentPage, sort]);

  return (
    <div>
      {loading && <p>Loading...</p>}

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th
              onClick={() => setSort('lastName,' + (sort.includes('asc') ? 'desc' : 'asc'))}
              style={{ cursor: 'pointer' }}
            >
              Last Name ↑↓
            </th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {page.content.map((c) => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{c.firstName}</td>
              <td>{c.lastName}</td>
              <td>{c.email}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div>
        <button
          disabled={currentPage === 0}
          onClick={() => setCurrentPage((p) => p - 1)}
        >
          Prev
        </button>
        <span>
          {' '}
          Page {currentPage + 1} of {page.totalPages}{' '}
        </span>
        <button
          disabled={currentPage + 1 >= page.totalPages}
          onClick={() => setCurrentPage((p) => p + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default CustomerTable;